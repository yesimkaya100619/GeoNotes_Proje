class ChecklistItem {
  final String? id;
  final String noteId;
  final String itemText;
  final bool isDone;

  ChecklistItem({
    this.id,
    required this.noteId,
    required this.itemText,
    this.isDone = false,
  });

  Map<String, dynamic> toMap() => {
    'note_id': noteId,
    'item_text': itemText,
    'is_done': isDone,
  };

  factory ChecklistItem.fromMap(Map<String, dynamic> map, String docId) =>
      ChecklistItem(
        id: docId,
        noteId: map['note_id'] ?? '',
        itemText: map['item_text'] ?? '',
        isDone: map['is_done'] ?? false,
      );
}
