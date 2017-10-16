package com.transfar.lucene;

import com.transfar.hr.config.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.lucene.IKAnalyzer;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class IndexFiles {
    public IndexFiles() {
        log= LogManager.getLogger(IndexFiles.class);
    }

    private Logger log=null;


    public static void main(String[] args) throws IOException {
        Config.getInstance();
        IndexFiles index=new IndexFiles();

        index.analyzer("基于java语言开发的轻量级的中文分词工具包");


    }

    public List<String> analyzer(String text) throws IOException {
        Analyzer analyzer= new IKAnalyzer();
        TokenStream ts=analyzer.tokenStream("",new StringReader(text));
        ts.reset();
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);

        while (ts.incrementToken()){//IOException
            //log.info();
            System.out.println(term.toString());
        }
        return null;
    }



    public IndexWriter getIndexWriter() throws IOException {
        Directory dir= FSDirectory.open(Paths.get("data/lucene"));
        Analyzer analyzer= new StandardAnalyzer();
        IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.APPEND);

        IndexWriter writer=new IndexWriter(dir,iwc);
        return writer;
    }

    /**
     *  Indexes a single document
     * @param writer
     * @param file
     * @param lastModified
     * @throws IOException
     */
    public void indexDoc(IndexWriter writer, Path file,long lastModified) throws IOException {
        InputStream stream= Files.newInputStream(file);
        Document doc=new Document();
        Field pathField=new StringField("path",file.toString(),Field.Store.YES);
        doc.add(pathField);
        doc.add(new LongPoint("modified",lastModified));

        Field content=new TextField("contents",new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)));
        doc.add(content);

        if(writer.getConfig().getOpenMode()== IndexWriterConfig.OpenMode.CREATE){
            System.out.println("adding "+file);
            writer.addDocument(doc);
        }
        else {
            System.out.println("updating "+file);
            writer.updateDocument(new Term("path",file.toString()),doc);
        }
    }


    /**
     *
     * @param writer
     * @param path
     * @throws IOException
     */
    public void indexDocs(final IndexWriter writer,Path path) throws IOException {
        if(Files.isDirectory(path)){
            Files.walkFileTree(path,new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)  {
                    try {
                        indexDoc(writer,file,attrs.lastModifiedTime().toMillis());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        }
        else {
            indexDoc(writer,path,Files.getLastModifiedTime(path).toMillis());
        }
    }

}
