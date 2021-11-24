/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 08/06/21, 4:30 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EquipmentModel implements Parcelable, Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image_path")
    @Expose
    private String imagePath;

    protected EquipmentModel(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        imagePath = in.readString();
    }

    public static final Creator<EquipmentModel> CREATOR = new Creator<EquipmentModel>() {
        @Override
        public EquipmentModel createFromParcel(Parcel in) {
            return new EquipmentModel(in);
        }

        @Override
        public EquipmentModel[] newArray(int size) {
            return new EquipmentModel[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(imagePath);
    }
}
