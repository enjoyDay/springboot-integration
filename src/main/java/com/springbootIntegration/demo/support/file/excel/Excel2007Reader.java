//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.springbootIntegration.demo.support.file.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class Excel2007Reader extends DefaultHandler {
    private final Logger logger = LogManager.getLogger();
    private RowReader rowReader;
    private SharedStringsTable sst;
    private String currentCellValue;
    private boolean nextIsString;
    private int sheetIndex = -1;
    private List<String> rowlist = new ArrayList();
    private int curRow = 0;
    private int curCol = 0;

    public void setRowReader(RowReader rowReader) {
        this.rowReader = rowReader;
    }

    public Excel2007Reader() {
    }

    public Excel2007Reader(RowReader rowReader) {
        this.rowReader = rowReader;
    }

    public void processOneSheet(String filename, int sheetId) throws Exception {
        try {
            OPCPackage pkg = OPCPackage.open(filename);
            Throwable var4 = null;

            try {
                XSSFReader r = new XSSFReader(pkg);
                SharedStringsTable sst = r.getSharedStringsTable();
                XMLReader parser = this.fetchSheetParser(sst);
                InputStream sheet2 = r.getSheet("rId" + sheetId);
                ++this.sheetIndex;
                InputSource sheetSource = new InputSource(sheet2);
                parser.parse(sheetSource);
                sheet2.close();
            } catch (Throwable var18) {
                var4 = var18;
                throw var18;
            } finally {
                if (pkg != null) {
                    if (var4 != null) {
                        try {
                            pkg.close();
                        } catch (Throwable var17) {
                            var4.addSuppressed(var17);
                        }
                    } else {
                        pkg.close();
                    }
                }

            }

        } catch (Exception var20) {
            throw var20;
        }
    }

    public void process(InputStream stream) throws Exception {
        try {
            OPCPackage pkg = OPCPackage.open(stream);
            Throwable var3 = null;

            try {
                XSSFReader r = new XSSFReader(pkg);
                SharedStringsTable sst = r.getSharedStringsTable();
                XMLReader parser = this.fetchSheetParser(sst);
                Iterator sheets = r.getSheetsData();

                while(sheets.hasNext()) {
                    this.curRow = 0;
                    ++this.sheetIndex;
                    InputStream sheet = (InputStream)sheets.next();
                    InputSource sheetSource = new InputSource(sheet);
                    parser.parse(sheetSource);
                    sheet.close();
                }
            } catch (Throwable var18) {
                var3 = var18;
                throw var18;
            } finally {
                if (pkg != null) {
                    if (var3 != null) {
                        try {
                            pkg.close();
                        } catch (Throwable var17) {
                            var3.addSuppressed(var17);
                        }
                    } else {
                        pkg.close();
                    }
                }

            }

        } catch (Exception var20) {
            throw var20;
        }
    }

    public void process(String filename) throws Exception {
        try {
            OPCPackage pkg = OPCPackage.open(filename);
            Throwable var3 = null;

            try {
                XSSFReader r = new XSSFReader(pkg);
                SharedStringsTable sst = r.getSharedStringsTable();
                XMLReader parser = this.fetchSheetParser(sst);
                Iterator sheets = r.getSheetsData();

                while(sheets.hasNext()) {
                    this.curRow = 0;
                    ++this.sheetIndex;
                    InputStream sheet = (InputStream)sheets.next();
                    InputSource sheetSource = new InputSource(sheet);
                    parser.parse(sheetSource);
                    sheet.close();
                }
            } catch (Throwable var18) {
                var3 = var18;
                throw var18;
            } finally {
                if (pkg != null) {
                    if (var3 != null) {
                        try {
                            pkg.close();
                        } catch (Throwable var17) {
                            var3.addSuppressed(var17);
                        }
                    } else {
                        pkg.close();
                    }
                }

            }

        } catch (Exception var20) {
            throw var20;
        }
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst) {
        try {
            XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
            this.sst = sst;
            parser.setContentHandler(this);
            return parser;
        } catch (SAXException var3) {
            throw new RuntimeException(var3);
        }
    }

    public void characters(char[] ch, int start, int length) {
        this.currentCellValue = this.currentCellValue + new String(ch, start, length);
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) {
        this.startCell(name, attributes);
        this.currentCellValue = "";
    }

    private void startCell(String name, Attributes attributes) {
        if ("c".equals(name)) {
            String cellType = attributes.getValue("t");
            if (cellType != null && "s".equals(cellType)) {
                this.nextIsString = true;
            } else {
                this.nextIsString = false;
            }
        }

    }

    public void endElement(String uri, String localName, String name) {
        if ("v".equals(name)) {
            this.endCellValue(name);
        } else {
            this.endRow(name);
        }

    }

    private void endCellValue(String name) {
        if (this.nextIsString) {
            try {
                int idx = Integer.parseInt(this.currentCellValue);
                this.currentCellValue = (new XSSFRichTextString(this.sst.getEntryAt(idx))).toString();
            } catch (Exception var3) {
                this.logger.error("", var3);
            }
        }

        String value = this.currentCellValue.trim();
        value = "".equals(value) ? " " : value;
        this.rowlist.add(this.curCol, value);
        ++this.curCol;
    }

    private void endRow(String name) {
        if ("row".equals(name)) {
            if (this.rowReader != null && !this.rowlist.isEmpty()) {
                try {
                    this.rowReader.invoke(this.sheetIndex, this.curRow, this.rowlist);
                } catch (Exception var3) {
                    this.logger.error("", "", var3);
                }
            }

            this.rowlist.clear();
            ++this.curRow;
            this.curCol = 0;
        }

    }
}
