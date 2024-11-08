package com.example.athena;

import android.graphics.Bitmap;

import com.example.athena.Models.QRCode;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QRCodeTest {

    private QRCode qrCode;

    /**
     * Sets up the test environment before each test.
     * Initializes the QRCode object.
     */
    @Before
    public void setUp() {
        qrCode = new QRCode();
    }

    /**
     * Tests the creation of the QR code.
     * Verifies that the QR code is generated without throwing exceptions and the bitmap is not null.
     *
     * @throws WriterException if an error occurs during QR code generation.
     */
    @Test
    public void testCreateQR() throws WriterException {
        String eventID = "Event123";
        qrCode.createQR(eventID);

        Bitmap bitmap = qrCode.getQRCode();
        assertNotNull("Bitmap should not be null after QR code generation", bitmap);
        assertEquals(400, bitmap.getWidth());
        assertEquals(400, bitmap.getHeight());
    }

    /**
     * Tests the getter for the QR code bitmap.
     * Verifies that the getter correctly retrieves the QR code bitmap.
     *
     * @throws WriterException if an error occurs during QR code generation.
     */
    @Test
    public void testGetQRCode() throws WriterException {
        String eventID = "Event456";
        qrCode.createQR(eventID);

        Bitmap bitmap = qrCode.getQRCode();
        assertNotNull("Bitmap should not be null after QR code generation", bitmap);
        assertEquals(400, bitmap.getWidth());
        assertEquals(400, bitmap.getHeight());
    }
}
