package com.itsvks.layouteditor;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.itsvks.layouteditor.utils.FileUtil;

import org.jetbrains.annotations.Contract;

import java.io.File;

public class LayoutFile implements Parcelable {

  private String path;
  private String designPath;
  public String name;

  public LayoutFile(String path, String designPath) {
    this.path = path;
    this.designPath = designPath;
    this.name = FileUtil.getLastSegmentFromPath(designPath);
  }

  //todo untested.
  public void rename(String newPath, String newDesignPah) {
    File newDesignFile = new File(newPath);
    File oldDesignFile = new File(getDesignPath());
    oldDesignFile.renameTo(newDesignFile);

    File newFile = new File(newDesignPah);
    File oldFile = new File(getDesignPath());
    oldFile.renameTo(newFile);

    designPath = newPath;
    path = newPath;
    name = FileUtil.getLastSegmentFromPath(designPath);
  }


  //todo currently delites only the design file, not the actual xml file.
  public void deleteLayout() {
    FileUtil.deleteFile(designPath);
  }

  //todo currently only saves design file
  public void saveLayout(String content) {
    FileUtil.writeFile(designPath, content);
  }

  public String getPath() {
    return path;
  }

  public String getDesignPath() {
    return designPath;
  }

  public String getName() {
    return name;
  }

  public String readDesignFile() {
    return FileUtil.readFile(designPath);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(@NonNull Parcel parcel, int flags) {
    parcel.writeString(designPath);
    parcel.writeString(path);
    parcel.writeString(name);
  }

  public static final Parcelable.Creator<LayoutFile> CREATOR =
    new Parcelable.Creator<>() {
      @NonNull
      @Contract("_ -> new")
      public LayoutFile createFromParcel(Parcel in) {
        return new LayoutFile(in);
      }

      @NonNull
      @Contract(value = "_ -> new", pure = true)
      public LayoutFile[] newArray(int size) {
        return new LayoutFile[size];
      }
    };

  private LayoutFile(@NonNull Parcel parcel) {
    designPath = parcel.readString();
    path = parcel.readString();
    name = parcel.readString();
  }
}
