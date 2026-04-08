class NotePhoto {
  final String? id;
  final String noteId;
  final String filePath;

  NotePhoto({this.id, required this.noteId, required this.filePath});

  Map<String, dynamic> toMap() => {'note_id': noteId, 'file_path': filePath};

  factory NotePhoto.fromMap(Map<String, dynamic> map, String docId) =>
      NotePhoto(
        id: docId,
        noteId: map['note_id'] ?? '',
        filePath: map['file_path'] ?? '',
      );
}
