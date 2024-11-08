package com.example.athena.Models;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCode {
    private Bitmap bitmap;
    public QRCode() {
    }
    public void createQR(String eventID) throws WriterException {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        this.bitmap = barcodeEncoder.encodeBitmap(eventID, BarcodeFormat.QR_CODE, 400, 400);
    }
    public Bitmap getQRCode() {
        return bitmap;
    }
}
