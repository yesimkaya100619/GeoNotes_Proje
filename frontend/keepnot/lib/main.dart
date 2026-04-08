import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'screens/home_screen.dart'; // Ana sayfamızı import ettik

void main() async {
  // Firebase ve Flutter bağlamını başlatıyoruz
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(); // Firebase bağlantısı

  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner:
          false, // Sağ üstteki "Debug" yazısını kaldırır
      title: 'GeoNotes',

      // Uygulama genel teması
      theme: ThemeData(
        useMaterial3: true,
        colorScheme: ColorScheme.fromSeed(
          seedColor: Colors.blueAccent,
          brightness: Brightness.light,
        ),
        appBarTheme: const AppBarTheme(
          centerTitle: true,
          backgroundColor: Colors.blueAccent,
          foregroundColor: Colors.white,
          elevation: 2,
        ),
        floatingActionButtonTheme: const FloatingActionButtonThemeData(
          backgroundColor: Colors.blueAccent,
          foregroundColor: Colors.white,
        ),
      ),

      // Uygulama açıldığında HomeScreen ekranı yüklenecek
      home: const HomeScreen(),
    );
  }
}
