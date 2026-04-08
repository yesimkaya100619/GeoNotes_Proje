import 'package:flutter/material.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:geolocator/geolocator.dart';

class LocationPickerScreen extends StatefulWidget {
  const LocationPickerScreen({super.key});

  @override
  State<LocationPickerScreen> createState() => _LocationPickerScreenState();
}

class _LocationPickerScreenState extends State<LocationPickerScreen> {
  LatLng? _pickedLocation;
  // _mapController uyarısını gidermek için kullanımı sağlandı veya kaldırılabilir.
  // Burada kamera hareketleri için tutmaya devam ediyoruz.
  GoogleMapController? _mapController;

  Future<LatLng> _getCurrentLocation() async {
    // Yeni standartlara göre LocationSettings kullanıldı
    Position position = await Geolocator.getCurrentPosition(
      locationSettings: const LocationSettings(accuracy: LocationAccuracy.high),
    );
    return LatLng(position.latitude, position.longitude);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Konum Seç"),
        backgroundColor: Colors.blueAccent,
        foregroundColor: Colors.white,
        actions: [
          if (_pickedLocation != null)
            IconButton(
              icon: const Icon(Icons.check, size: 30),
              onPressed: () => Navigator.pop(context, _pickedLocation),
            ),
        ],
      ),
      body: FutureBuilder<LatLng>(
        future: _getCurrentLocation(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text("Konum alınamadı: ${snapshot.error}"));
          }

          final initialPos = snapshot.data!;

          return GoogleMap(
            initialCameraPosition: CameraPosition(target: initialPos, zoom: 16),
            onMapCreated: (controller) => _mapController = controller,
            myLocationEnabled: true,
            myLocationButtonEnabled: true,
            onTap: (latLng) {
              setState(() {
                _pickedLocation = latLng;
              });
              // Seçilen yere kamerayı hafifçe odakla
              _mapController?.animateCamera(CameraUpdate.newLatLng(latLng));
            },
            markers: _pickedLocation == null
                ? {}
                : {
                    Marker(
                      markerId: const MarkerId("selected-location"),
                      position: _pickedLocation!,
                      draggable: true,
                      onDragEnd: (newLatLng) {
                        setState(() {
                          _pickedLocation = newLatLng;
                        });
                      },
                    ),
                  },
          );
        },
      ),
    );
  }
}
