package com.example.piratica;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

class NetIpAdd {
    public String CheckIp(String DomainName){
        String host = DomainName;
        String result;
        try {
            InetAddress address = InetAddress . getByName ( host ) ;
             result= ("IP address : " + address . toString () ) ;
        }
        catch ( UnknownHostException e ) {
             result =  (" Could not find " + host ) ;
        }

        return result;
    }

}
