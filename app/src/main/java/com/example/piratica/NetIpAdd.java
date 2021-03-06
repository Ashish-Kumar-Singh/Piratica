package com.example.piratica;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
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
            String host = params[0];
            int port = 443;
            int connectTimeout = 5000;
            SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();
            SSLSocket socket = (SSLSocket) factory.createSocket();
            socket.connect(new InetSocketAddress(host, port), connectTimeout);
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


