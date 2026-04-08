class Folder {
  final String? id;
  final String userId;
  final String name;
  final String colorCode;

  Folder({
    this.id,
    required this.userId,
    required this.name,
    required this.colorCode,
  });

  Map<String, dynamic> toMap() => {
    'user_id': userId,
    'name': name,
    'color_code': colorCode,
  };

  factory Folder.fromMap(Map<String, dynamic> map, String docId) => Folder(
    id: docId,
    userId: map['user_id'] ?? '',
    name: map['name'] ?? '',
    colorCode: map['color_code'] ?? '',
  );
}
