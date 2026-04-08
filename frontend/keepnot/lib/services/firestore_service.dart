import 'package:cloud_firestore/cloud_firestore.dart';
import '../data/models/note.dart';
import '../data/models/folder.dart';
import '../data/models/location.dart';

class FirestoreService {
  final FirebaseFirestore _db = FirebaseFirestore.instance;

  // --- NOT İŞLEMLERİ ---
  Future<String> addNoteAndGetId(Note note) async {
    DocumentReference docRef = await _db.collection('notes').add(note.toMap());
    return docRef.id;
  }

  Future<void> addNote(Note note) async {
    await _db.collection('notes').add(note.toMap());
  }

  Stream<List<Note>> getNotes(String userId) {
    return _db
        .collection('notes')
        .where('user_id', isEqualTo: userId)
        .snapshots()
        .map(
          (snapshot) => snapshot.docs
              .map((doc) => Note.fromMap(doc.data(), doc.id))
              .toList(),
        );
  }

  Future<void> updateNote(Note note) async {
    if (note.id != null) {
      await _db.collection('notes').doc(note.id).update(note.toMap());
    }
  }

  Future<void> deleteNote(String noteId) async {
    await _db.collection('notes').doc(noteId).delete();
  }

  // --- KONUM (LOCATION) İŞLEMLERİ ---
  Future<void> addLocation(Location location) async {
    await _db.collection('locations').add(location.toMap());
  }

  Future<Location?> getLocationByNoteId(String noteId) async {
    var query = await _db
        .collection('locations')
        .where('note_id', isEqualTo: noteId)
        .limit(1)
        .get();

    if (query.docs.isNotEmpty) {
      return Location.fromMap(query.docs.first.data(), query.docs.first.id);
    }
    return null;
  }

  // --- KLASÖR İŞLEMLERİ ---
  Future<void> addFolder(Folder folder) async {
    await _db.collection('folders').add(folder.toMap());
  }

  Stream<List<Folder>> getFolders(String userId) {
    return _db
        .collection('folders')
        .where('user_id', isEqualTo: userId)
        .snapshots()
        .map(
          (snapshot) => snapshot.docs
              .map((doc) => Folder.fromMap(doc.data(), doc.id))
              .toList(),
        );
  }
}
