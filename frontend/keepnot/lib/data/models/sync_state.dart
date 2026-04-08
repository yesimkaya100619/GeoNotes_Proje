class SyncState {
  final String? id;
  final String noteId;
  final String status;
  final String lastSync;

  SyncState({
    this.id,
    required this.noteId,
    required this.status,
    required this.lastSync,
  });

  Map<String, dynamic> toMap() => {
    'note_id': noteId,
    'status': status,
    'last_sync': lastSync,
  };

  factory SyncState.fromMap(Map<String, dynamic> map, String docId) =>
      SyncState(
        id: docId,
        noteId: map['note_id'] ?? '',
        status: map['status'] ?? 'pending',
        lastSync: map['last_sync'] ?? '',
      );
}
