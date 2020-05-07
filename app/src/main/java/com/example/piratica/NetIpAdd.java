package com.example.piratica;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.bind.DatatypeConverter;


class NetIpAdd extends AsyncTask<String, Integer, String> {
    //Gets the Thumbprint of website on Network.
    @Override
    protected String doInBackground(String... params) {
        String LANThumbprint = null;
        try {
            SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();
            SSLSocket socket = (SSLSocket) factory.createSocket(params[0], 443);
            socket.setSoTimeout(300);
                socket.startHandshake();
                Certificate[] certs = socket.getSession().getPeerCertificates();
                Certificate cert = certs[0];
                LANThumbprint = DatatypeConverter.printHexBinary(
                        MessageDigest.getInstance("SHA-1").digest(
                                cert.getEncoded())).toLowerCase();

        } catch (NoSuchAlgorithmException | CertificateEncodingException | IOException e) {
//            e.printStackTrace();
            Log.e("Thumprint from Network", "Unable to Fetch");
        }
        return LANThumbprint;
    }
}


