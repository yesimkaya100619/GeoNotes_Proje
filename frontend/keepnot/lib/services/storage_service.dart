import 'dart:io';
import 'package:flutter/foundation.dart'; // debugPrint için eklendi
import 'package:firebase_storage/firebase_storage.dart';
import 'package:image_picker/image_picker.dart';
import 'package:path/path.dart' as path;

class StorageService {
  final FirebaseStorage _storage = FirebaseStorage.instance;
  final ImagePicker _picker = ImagePicker();

  Future<File?> pickImage(ImageSource source) async {
    try {
      final XFile? pickedFile = await _picker.pickImage(
        source: source,
        imageQuality: 50,
      );
      if (pickedFile != null) {
        return File(pickedFile.path);
      }
    } catch (e) {
      debugPrint("Resim seçme hatası: $e");
    }
    return null;
  }

  Future<String?> uploadNoteImage(File image) async {
    try {
      String fileName =
          "${DateTime.now().millisecondsSinceEpoch}${path.extension(image.path)}";
      Reference ref = _storage.ref().child('note_images').child(fileName);
      UploadTask uploadTask = ref.putFile(image);
      TaskSnapshot snapshot = await uploadTask;
      return await snapshot.ref.getDownloadURL();
    } catch (e) {
      debugPrint("Storage yükleme hatası: $e");
      return null;
    }
  }
}
