class Tag {
  final String? id;
  final String tagName;

  Tag({this.id, required this.tagName});

  Map<String, dynamic> toMap() => {'tag_name': tagName};

  factory Tag.fromMap(Map<String, dynamic> map, String docId) =>
      Tag(id: docId, tagName: map['tag_name'] ?? '');
}
