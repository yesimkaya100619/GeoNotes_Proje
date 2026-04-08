class Note {
  final String? id;
  final String userId;
  final String? folderId;
  final String title;
  final String content;
  final String updatedAt;
  final String? imageUrl;
  final String? localPath;

  Note({
    this.id,
    required this.userId,
    this.folderId,
    required this.title,
    required this.content,
    required this.updatedAt,
    this.imageUrl,
    this.localPath,
  });

  Map<String, dynamic> toMap() => {
    'user_id': userId,
    'folder_id': folderId,
    'title': title,
    'content': content,
    'updated_at': updatedAt,
    'image_url': imageUrl,
    'local_path': localPath,
  };

  factory Note.fromMap(Map<String, dynamic> map, String docId) => Note(
    id: docId,
    userId: map['user_id'] ?? '',
    folderId: map['folder_id'],
    title: map['title'] ?? '',
    content: map['content'] ?? '',
    updatedAt: map['updated_at'] ?? '',
    imageUrl: map['image_url'],
    localPath: map['local_path'],
  );
}
