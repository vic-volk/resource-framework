package ru.vlk.resources.framework.cli;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.document.Document;
import ru.vlk.resources.framework.model.Resource;
import ru.vlk.resources.framework.model.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ResourceManagerImpl implements ResourceManager {

    private Directory index;
    private IndexWriterConfig config;
    private IndexWriter w;
    private IndexReader reader;
    private IndexSearcher searcher;
    private StandardAnalyzer analyzer;

    public ResourceManagerImpl(File indexFile) throws IOException {
        Path path = indexFile.toPath();
        analyzer = new StandardAnalyzer();
        index = new SimpleFSDirectory(path);
    }

    public void addResource(Resource resource) throws IOException {
        addDoc(resource.getUrl(), resource.getDescription(),
                resource.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));
        w.close();
    }

    public Set<Resource> searchResources(Tag tag) {
        return null;
    }

    public Set<Resource> searchResources(Set<Tag> tags) {
        return null;
    }

    public Set<Resource> searchAll() {
        return null;
    }

    public Set<Resource> search(String term) throws IOException, ParseException {
        reader = DirectoryReader.open(index);
        searcher = new IndexSearcher(reader);
        config = new IndexWriterConfig(analyzer);
        w = new IndexWriter(index, config);
        Query q = new QueryParser("title", analyzer).parse(term);
        int hitsPerPage = 10;
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;
        System.out.println("Found " + hits.length + " hits.");
        Set<Resource> resources = new HashSet<>();
        for(int i=0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
            Resource resource = new Resource();
            resource.setUrl(d.get("url"));
            resource.setDescription(d.get("description"));
            resources.add(resource);
        }
        return resources;
    }

    private void addDoc(String url, String description, Set<String> tags) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("description", description, Field.Store.YES));
        doc.add(new StringField("url", url, Field.Store.YES));
        tags.forEach(t -> doc.add(new StringField("tag", t, Field.Store.YES)));
        w.addDocument(doc);
    }
}
