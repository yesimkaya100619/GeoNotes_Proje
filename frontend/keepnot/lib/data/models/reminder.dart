class Reminder {
  final String? id;
  final String noteId;
  final String remindAt;
  final bool isActive;

  Reminder({
    this.id,
    required this.noteId,
    required this.remindAt,
    this.isActive = true,
  });

  Map<String, dynamic> toMap() => {
    'note_id': noteId,
    'remind_at': remindAt,
    'is_active': isActive,
  };

  factory Reminder.fromMap(Map<String, dynamic> map, String docId) => Reminder(
    id: docId,
    noteId: map['note_id'] ?? '',
    remindAt: map['remind_at'] ?? '',
    isActive: map['is_active'] ?? true,
  );
}
