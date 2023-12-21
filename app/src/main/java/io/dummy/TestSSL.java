package src.main.java.io.dummy;

import java.io.IOException;
/*
// Imports for servlet-4.0 and servlet-5.0
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
*/

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSession;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

@WebServlet(urlPatterns = "/testssl")
public class TestSSL extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Create a socket factory
        SocketFactory factory = SSLSocketFactory.getDefault();

        // Create a socket to the host
        SSLSocket socket = (SSLSocket) factory.createSocket(request.getParameter("host"), Integer.parseInt(request.getParameter("port")));

        // List socket information
        response.getWriter().append("<h2>Socket Information</h2>");
        response.getWriter().append("<b>Enabled Protocols: </b>").append(String.join(", ", socket.getEnabledProtocols())).append("<br>");
        response.getWriter().append("<b>Enabled Cipher Suites: </b>").append(String.join(", ", socket.getEnabledCipherSuites())).append("<br>");

        // List if peer authentication is required
        response.getWriter().append("<h2>Peer Authentication</h2>");
        response.getWriter().append("<b>Need Client Authentication: </b>").append(String.valueOf(socket.getNeedClientAuth())).append("<br>");
        response.getWriter().append("<b>Want Client Authentication: </b>").append(String.valueOf(socket.getWantClientAuth())).append("<br>");

        // Start the handshake
        SSLSession session = socket.getSession();

        // List session information
        response.getWriter().append("<h2>Session Information</h2>");
        response.getWriter().append("<b>Peer Host: </b>").append(session.getPeerHost()).append("<br>");
        response.getWriter().append("<b>Peer Port: </b>").append(String.valueOf(session.getPeerPort())).append("<br>");
        response.getWriter().append("<b>Cipher Suite: </b>").append(session.getCipherSuite()).append("<br>");
        response.getWriter().append("<b>Protocol: </b>").append(session.getProtocol()).append("<br>");

        // List peer certificates
        response.getWriter().append("<h2>Peer Certificates</h2>");
        for (Certificate cert : session.getPeerCertificates()) {
            response.getWriter().append("<b>Subject DN: </b>").append(((X509Certificate) cert).getSubjectX500Principal().getName()).append("<br>");
            response.getWriter().append("<b>Issuer DN: </b>").append(((X509Certificate) cert).getIssuerX500Principal().getName()).append("<br>");
            response.getWriter().append("<b>Serial Number: </b>").append(((X509Certificate) cert).getSerialNumber().toString()).append("<br>");
            response.getWriter().append("<b>Not Before: </b>").append(((X509Certificate) cert).getNotBefore().toString()).append("<br>");
            response.getWriter().append("<b>Not After: </b>").append(((X509Certificate) cert).getNotAfter().toString()).append("<br>");
            response.getWriter().append("<b>Signature Algorithm: </b>").append(((X509Certificate) cert).getSigAlgName()).append("<br>");
            response.getWriter().append("<b>Version: </b>").append(String.valueOf(((X509Certificate) cert).getVersion())).append("<br>");
            response.getWriter().append("<b>Public Key: </b>").append(((X509Certificate) cert).getPublicKey().toString()).append("<br>");
            response.getWriter().append("<br>");
        }

        // List local certificates
        response.getWriter().append("<h2>Local Certificates</h2>");
        try {
            session.getLocalCertificates();
            for (Certificate cert : session.getLocalCertificates()) {
                response.getWriter().append("<b>Subject DN: </b>").append(((X509Certificate) cert).getSubjectX500Principal().getName()).append("<br>");
                response.getWriter().append("<b>Issuer DN: </b>").append(((X509Certificate) cert).getIssuerX500Principal().getName()).append("<br>");
                response.getWriter().append("<b>Serial Number: </b>").append(((X509Certificate) cert).getSerialNumber().toString()).append("<br>");
                response.getWriter().append("<b>Not Before: </b>").append(((X509Certificate) cert).getNotBefore().toString()).append("<br>");
                response.getWriter().append("<b>Not After: </b>").append(((X509Certificate) cert).getNotAfter().toString()).append("<br>");
                response.getWriter().append("<b>Signature Algorithm: </b>").append(((X509Certificate) cert).getSigAlgName()).append("<br>");
                response.getWriter().append("<b>Version: </b>").append(String.valueOf(((X509Certificate) cert).getVersion())).append("<br>");
                response.getWriter().append("<b>Public Key: </b>").append(((X509Certificate) cert).getPublicKey().toString()).append("<br>");
                response.getWriter().append("<br>");
            }
        }catch (NullPointerException e){
            response.getWriter().append("<b>Local Certificates: </b>").append("null").append("<br>");
            response.getWriter().append("<br>");
        }

        // Close the socket
        socket.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }
}
