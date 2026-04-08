import 'dart:io';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:google_maps_flutter/google_maps_flutter.dart';
import 'package:path_provider/path_provider.dart'; // YENİ: Dosya yolu için eklendi
import '../services/firestore_service.dart';
import '../services/storage_service.dart';
import '../data/models/note.dart';
import '../data/models/location.dart' as my_model;
import 'location_picker_screen.dart';

class AddNoteScreen extends StatefulWidget {
  const AddNoteScreen({super.key});

  @override
  State<AddNoteScreen> createState() => _AddNoteScreenState();
}

class _AddNoteScreenState extends State<AddNoteScreen> {
  final _titleController = TextEditingController();
  final _contentController = TextEditingController();

  File? _image;
  LatLng? _selectedLocation;

  final _storageService = StorageService();
  final _firestoreService = FirestoreService();
  bool _isLoading = false;

  Future<void> _takePhoto() async {
    final pickedImage = await _storageService.pickImage(ImageSource.camera);
    if (pickedImage != null) {
      setState(() => _image = pickedImage);
    }
  }

  Future<void> _pickLocation() async {
    final LatLng? result = await Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => const LocationPickerScreen()),
    );

    if (result != null) {
      setState(() {
        _selectedLocation = result;
      });
    }
  }

  Future<void> _saveNote() async {
    if (_titleController.text.isEmpty) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text("Lütfen bir başlık girin!")));
      return;
    }

    setState(() => _isLoading = true);

    try {
      String? imagePathToSave;

      if (_image != null) {
        // --- YENİ MANTIK: BULUTA DEĞİL TELEFONA KAYDET ---
        // Uygulamanın özel klasörünü buluyoruz
        final directory = await getApplicationDocumentsDirectory();
        final String path = directory.path;
        // Dosyayı benzersiz bir isimle oraya kopyalıyoruz
        final String fileName =
            'note_${DateTime.now().millisecondsSinceEpoch}.png';
        final File localImage = await _image!.copy('$path/$fileName');

        // Firestore'a kaydedilecek olan şey artık internet linki değil, dosya yolu
        imagePathToSave = localImage.path;
      }

      final newNote = Note(
        userId: "test_user_123",
        title: _titleController.text,
        content: _contentController.text,
        updatedAt: DateTime.now().toIso8601String(),
        imageUrl: imagePathToSave, // Artık path tutuyor
      );

      String noteDocId = await _firestoreService.addNoteAndGetId(newNote);

      if (_selectedLocation != null) {
        final noteLoc = my_model.Location(
          noteId: noteDocId,
          latitude: _selectedLocation!.latitude,
          longitude: _selectedLocation!.longitude,
          addressName: "Seçilen Konum",
        );
        await _firestoreService.addLocation(noteLoc);
      }

      if (mounted) {
        Navigator.pop(context);
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text("Not başarıyla kaydedildi!")),
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(
          context,
        ).showSnackBar(SnackBar(content: Text("Hata oluştu: $e")));
      }
    } finally {
      if (mounted) setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Yeni GeoNote"),
        actions: [
          if (!_isLoading)
            IconButton(onPressed: _saveNote, icon: const Icon(Icons.check)),
        ],
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : SingleChildScrollView(
              padding: const EdgeInsets.all(16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  GestureDetector(
                    onTap: _takePhoto,
                    child: Container(
                      height: 200,
                      decoration: BoxDecoration(
                        color: Colors.grey[200],
                        borderRadius: BorderRadius.circular(12),
                        border: Border.all(color: Colors.grey[400]!),
                      ),
                      child: _image != null
                          ? ClipRRect(
                              borderRadius: BorderRadius.circular(12),
                              child: Image.file(_image!, fit: BoxFit.cover),
                            )
                          : const Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              children: [
                                Icon(
                                  Icons.camera_alt,
                                  size: 50,
                                  color: Colors.grey,
                                ),
                                Text("Fotoğraf Çek"),
                              ],
                            ),
                    ),
                  ),
                  const SizedBox(height: 20),
                  TextField(
                    controller: _titleController,
                    decoration: const InputDecoration(
                      labelText: "Başlık",
                      border: OutlineInputBorder(),
                    ),
                  ),
                  const SizedBox(height: 15),
                  TextField(
                    controller: _contentController,
                    maxLines: 5,
                    decoration: const InputDecoration(
                      labelText: "Not Detayı",
                      border: OutlineInputBorder(),
                    ),
                  ),
                  const SizedBox(height: 20),
                  ElevatedButton.icon(
                    onPressed: _pickLocation,
                    icon: Icon(
                      Icons.location_on,
                      color: _selectedLocation == null
                          ? Colors.grey
                          : Colors.red,
                    ),
                    label: Text(
                      _selectedLocation == null
                          ? "Haritadan Yer Seç"
                          : "Konum Seçildi",
                    ),
                  ),
                  const SizedBox(height: 30),
                  ElevatedButton(
                    onPressed: _saveNote,
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.blueAccent,
                      foregroundColor: Colors.white,
                    ),
                    child: const Text("NOTU KAYDET"),
                  ),
                ],
              ),
            ),
    );
  }
}
