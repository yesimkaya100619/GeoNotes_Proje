import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import '../services/firestore_service.dart';
import '../data/models/location.dart' as model; // Prefix kullanımı
import '../data/models/note.dart';

class MainMapScreen extends StatefulWidget {
  const MainMapScreen({super.key});

  @override
  State<MainMapScreen> createState() => _MainMapScreenState();
}

class _MainMapScreenState extends State<MainMapScreen> {
  final FirestoreService _firestoreService = FirestoreService();
  final Set<Marker> _markers = {};
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadNoteLocations();
  }

  Future<void> _loadNoteLocations() async {
    try {
      final snapshot = await FirebaseFirestore.instance
          .collection('notes')
          .where('user_id', isEqualTo: 'test_user_123')
          .get();

      List<Note> notes = snapshot.docs
          .map((doc) => Note.fromMap(doc.data(), doc.id))
          .toList();

      for (var note in notes) {
        if (note.id != null) {
          model.Location? loc = await _firestoreService.getLocationByNoteId(
            note.id!,
          );

          if (loc != null) {
            setState(() {
              _markers.add(
                Marker(
                  markerId: MarkerId(note.id!),
                  position: LatLng(loc.latitude, loc.longitude),
                  infoWindow: InfoWindow(
                    title: note.title,
                    snippet: note.content.length > 30
                        ? "${note.content.substring(0, 30)}..."
                        : note.content,
                  ),
                  icon: BitmapDescriptor.defaultMarkerWithHue(
                    BitmapDescriptor.hueAzure,
                  ),
                ),
              );
            });
          }
        }
      }
    } catch (e) {
      debugPrint("Harita yükleme hatası: $e");
    } finally {
      setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Notlarımın Haritası"),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () {
              setState(() {
                _markers.clear();
                _isLoading = true;
              });
              _loadNoteLocations();
            },
          ),
        ],
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : GoogleMap(
              initialCameraPosition: const CameraPosition(
                target: LatLng(39.9334, 32.8597), // Ankara
                zoom: 6,
              ),
              markers: _markers,
              myLocationEnabled: true,
              mapType: MapType.normal,
              onMapCreated: (GoogleMapController controller) {
                if (_markers.isNotEmpty) {
                  controller.animateCamera(
                    CameraUpdate.newLatLngZoom(_markers.first.position, 12),
                  );
                }
              },
            ),
    );
  }
}
