package jenkins.plugins.rocketchatnotifier.workflow.attachments;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

/**
 * Created by ben on 07/02/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageAttachment extends AbstractDescribableImpl<MessageAttachment> {

  private String color;
  private String text;
  private String ts;

  @JsonProperty("thumb_url")
  private String thumbUrl;
  @JsonProperty("message_link")
  private String messageLink;
  private Boolean collapsed;
  @JsonProperty("author_name")
  private String authorName;
  private final String title;

  @JsonProperty("author_link")
  private String authorLink;
  @JsonProperty("author_icon")
  private String authorIcon;
  @JsonProperty("title_link")
  private String titleLink;
  @JsonProperty("title_link_download")
  private String titleLinkDownload;
  @JsonProperty("image_url")
  private String imageUrl;
  @JsonProperty("audio_url")
  private String audioUrl;
  @JsonProperty("video_url")
  private String videoUrl;

  @DataBoundConstructor
  public MessageAttachment(String title) {
    this.title = title;
  }

  public String getColor() {
    return color;
  }

  @DataBoundSetter
  public void setColor(final String color) {
    this.color = color;
  }

  public String getText() {
    return text;
  }

  @DataBoundSetter
  public void setText(final String text) {
    this.text = text;
  }

  public String getTs() {
    return ts;
  }

  @DataBoundSetter
  public void setTs(final String ts) {
    this.ts = ts;
  }

  public String getThumbUrl() {
    return thumbUrl;
  }

  @DataBoundSetter
  public void setThumbUrl(final String thumbUrl) {
    this.thumbUrl = thumbUrl;
  }

  public String getMessageLink() {
    return messageLink;
  }

  @DataBoundSetter
  public void setMessageLink(final String messageLink) {
    this.messageLink = messageLink;
  }

  @DataBoundSetter
  public void setCollapsed(final Boolean collapsed) {
    if (collapsed)
      this.collapsed = collapsed;
  }

  public String getAuthorName() {
    return authorName;
  }

  @DataBoundSetter
  public void setAuthorName(final String authorName) {
    this.authorName = authorName;
  }

  public String getTitle() {
    return title;
  }

  public Boolean getCollapsed() {
    return collapsed;
  }

  public String getAuthorLink() {
    return authorLink;
  }

  public void setAuthorLink(final String authorLink) {
    this.authorLink = authorLink;
  }

  public String getAuthorIcon() {
    return authorIcon;
  }

  @DataBoundSetter
  public void setAuthorIcon(final String authorIcon) {
    this.authorIcon = authorIcon;
  }

  public String getTitleLink() {
    return titleLink;
  }

  @DataBoundSetter
  public void setTitleLink(final String titleLink) {
    this.titleLink = titleLink;
  }

  public String getTitleLinkDownload() {
    return titleLinkDownload;
  }

  @DataBoundSetter
  public void setTitleLinkDownload(final String titleLinkDownload) {
    this.titleLinkDownload = titleLinkDownload;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  @DataBoundSetter
  public void setImageUrl(final String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getAudioUrl() {
    return audioUrl;
  }

  @DataBoundSetter
  public void setAudioUrl(final String audioUrl) {
    this.audioUrl = audioUrl;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  @DataBoundSetter
  public void setVideoUrl(final String videoUrl) {
    this.videoUrl = videoUrl;
  }

  @Extension
  @JsonIgnoreType
  public static class DescriptorImpl extends Descriptor<MessageAttachment> {
    public String getDisplayName() {
      return "";
    }
  }
}
