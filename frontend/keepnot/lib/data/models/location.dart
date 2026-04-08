class Location {
  final String? id;
  final String noteId;
  final double latitude;
  final double longitude;
  final String? addressName;

  Location({
    this.id,
    required this.noteId,
    required this.latitude,
    required this.longitude,
    this.addressName,
  });

  Map<String, dynamic> toMap() => {
    'note_id': noteId,
    'latitude': latitude,
    'longitude': longitude,
    'address_name': addressName,
  };

  factory Location.fromMap(Map<String, dynamic> map, String docId) => Location(
    id: docId,
    noteId: map['note_id'] ?? '',
    latitude: (map['latitude'] as num).toDouble(),
    longitude: (map['longitude'] as num).toDouble(),
    addressName: map['address_name'],
  );
}
