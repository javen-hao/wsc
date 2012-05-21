/*
 * Copyright (c) 2005, salesforce.com, inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided
 * that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice, this list of conditions and the
 *    following disclaimer.
 *
 *    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and
 *    the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 *    Neither the name of salesforce.com, inc. nor the names of its contributors may be used to endorse or
 *    promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.sforce.ws.wsdl;

import com.sforce.ws.parser.XmlInputStream;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;

/**
 * Use this factory to create WSDL data model.
 *
 * @author http://cheenath.com
 * @version 1.0
 * @since 1.0   Nov 5, 2005
 */
public class WsdlFactory {

    /**
     * @param url url of the wsdl
     * @return parsed definitions
     * @throws WsdlParseException failed to parse wsdl
     */
    public static Definitions create(String url) throws WsdlParseException {
        InputStream in;

        try {
          try {
              in = new URL(url).openStream();
          } catch (MalformedURLException e) {
              in = new FileInputStream(url);
          }
        }catch (IOException e) {
            throw new WsdlParseException(e);
        }

        XmlInputStream parser = new XmlInputStream();
        WsdlParser wsdlParser = new WsdlParser(parser);
        Definitions definitions = new Definitions();
        wsdlParser.setInput(in, "UTF-8");
        definitions.read(wsdlParser);
        return definitions;
    }

    public static Definitions createFromString(String wsdl) throws WsdlParseException {
        XmlInputStream parser = new XmlInputStream();
        WsdlParser wsdlParser = new WsdlParser(parser);
        Definitions definitions = new Definitions();
        ByteArrayInputStream bio = new ByteArrayInputStream(wsdl.getBytes());
        wsdlParser.setInput(bio, "UTF-8");
        definitions.read(wsdlParser);
        return definitions;
     }
}
