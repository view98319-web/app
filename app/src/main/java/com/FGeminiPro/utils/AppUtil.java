package com.FGeminiPro.utils;

public class AppUtil {
	public static String error() {
		String error = "<!DOCTYPE html>\n<html lang=\"bn\">\n<head>\n  <meta charset=\"UTF-8\">\n  <meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n  <title>No Internet</title>\n  <style>\n    body {\n      margin:0; padding:0;\n      height:100vh;\n      display:flex;\n      align-items:center;\n      justify-content:center;\n      background:linear-gradient(135deg,#1e293b,#0f172a);\n      font-family: \"Segoe UI\",sans-serif;\n      color:#f1f5f9;\n    }\n    .card {\n      text-align:center;\n      padding:32px 24px;\n      border-radius:16px;\n      background:rgba(255,255,255,0.05);\n      box-shadow:0 8px 30px rgba(0,0,0,0.5);\n      max-width:360px;\n    }\n    .icon {\n      width:80px;height:80px;\n      margin:0 auto 20px;\n    }\n    h1 {\n      font-size:22px;\n      margin:0 0 10px;\n    }\n    p {\n      color:#cbd5e1;\n      margin:0 0 20px;\n      font-size:15px;\n    }\n    button {\n      padding:10px 18px;\n      border:none;\n      border-radius:8px;\n      font-size:15px;\n      font-weight:600;\n      background:#38bdf8;\n      color:#0f172a;\n      cursor:pointer;\n      transition:0.2s;\n    }\n    button:hover {\n      background:#0ea5e9;\n    }\n  </style>\n</head>\n<body>\n  <div class=\"card\">\n    <div class=\"icon\">\n      <!-- ছোট WiFi অফ SVG -->\n      <svg viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"#38bdf8\" stroke-width=\"1.8\" stroke-linecap=\"round\" stroke-linejoin=\"round\">\n        <path d=\"M2 8.82a15 15 0 0 1 20 0\"></path>\n        <path d=\"M5 12.5a10 10 0 0 1 14 0\"></path>\n        <path d=\"M8.5 16.1a5 5 0 0 1 7 0\"></path>\n        <line x1=\"2\" y1=\"2\" x2=\"22\" y2=\"22\"></line>\n      </svg>\n    </div>\n    <h1>ইন্টারনেট নেই</h1>\n    <p>অনুগ্রহ করে আপনার সংযোগ চেক করুন এবং আবার চেষ্টা করুন।</p>\n    \n  </div>\n</body>\n</html>";
		
		return error;
		//button onclick="location.reload()">পুনরায় চেষ্টা করুন</button>
	}
    
    
}
