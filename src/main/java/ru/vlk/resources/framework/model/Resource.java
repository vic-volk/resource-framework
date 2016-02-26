package ru.vlk.resources.framework.model;

import java.util.Set;

public class Resource {

    public String url;
    public Set<Tag> tags;
    public String description;
    public String time;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    //TODO: finish
//    public static Builder builder() {
//        return new Builder();
//    }
//
//    public static class Builder {
//
//        public Builder resource()
//    }
}
