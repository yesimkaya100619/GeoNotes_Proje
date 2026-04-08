class Favorite {
  final String? id;
  final String noteId;
  final String addedAt;

  Favorite({this.id, required this.noteId, required this.addedAt});

  Map<String, dynamic> toMap() => {'note_id': noteId, 'added_at': addedAt};

  factory Favorite.fromMap(Map<String, dynamic> map, String docId) => Favorite(
    id: docId,
    noteId: map['note_id'] ?? '',
    addedAt: map['added_at'] ?? '',
  );
}
