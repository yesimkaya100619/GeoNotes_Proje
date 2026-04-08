class User {
  final String? id;
  final String username;
  final String email;
  final String createdAt;

  User({
    this.id,
    required this.username,
    required this.email,
    required this.createdAt,
  });

  Map<String, dynamic> toMap() => {
    'username': username,
    'email': email,
    'created_at': createdAt,
  };

  factory User.fromMap(Map<String, dynamic> map, String docId) => User(
    id: docId,
    username: map['username'] ?? '',
    email: map['email'] ?? '',
    createdAt: map['created_at'] ?? '',
  );
}
