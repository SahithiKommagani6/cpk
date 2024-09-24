/*!
* Copyright 2002 - 2017 Webdetails, a Hitachi Vantara company.  All rights reserved.
*
* This software was developed by Webdetails and is provided under the terms
* of the Mozilla Public License, Version 2.0, or any later version. You may not use
* this file except in compliance with the license. If you need a copy of the license,
* please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
*
* Software distributed under the Mozilla Public License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
* the license for the specific language governing your rights and limitations.
*/

package pt.webdetails.cpk.elements.impl.kettleoutputs;


import pt.webdetails.cpk.elements.impl.KettleResult;
import pt.webdetails.cpk.utils.CpkUtils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

public class SingleCellKettleOutput extends KettleOutput {

  @Override
  public void processResult( KettleResult result ) {
    this.logger.debug( "Process Single Cell - print it" );

    try {
      // at least one row to process?
      if ( !result.getRows().isEmpty() ) {
        Object cell = result.getRows().get( 0 ).getData()[ 0 ];
        byte[] resultContent = cell.toString().getBytes( ENCODING );
        int attachmentSize = resultContent.length;
        ByteArrayInputStream resultInputStream = new ByteArrayInputStream( resultContent );
        String mimeType = this.getConfiguration().getMimeType();
        String defaultAttachmentName = this.getConfiguration().getAttachmentName();
        String attachmentName = defaultAttachmentName != null ? defaultAttachmentName : "singleCell";

        CpkUtils.send( this.getResponse(), resultInputStream, mimeType, attachmentName,
          this.getConfiguration().getSendResultAsAttachment(), attachmentSize );
      }
    } catch ( UnsupportedEncodingException ex ) {
      this.logger.error( "Unsupported encoding.", ex );
    }
  }

}
