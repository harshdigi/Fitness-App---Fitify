/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 08/06/21, 4:51 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExercisesModel implements Parcelable, Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("muscle_group")
    @Expose
    private String muscleGroup;
    @SerializedName("muscle_worked")
    @Expose
    private String muscleWorked;
    @SerializedName("equipment")
    @Expose
    private List<EquipmentModel> equipment = null;
    @SerializedName("video_path")
    @Expose
    private String videoPath;
    @SerializedName("image_path")
    @Expose
    private String imagePath;
    @SerializedName("workout")
    @Expose
    private List<WorkoutModel> workout = null;

    protected ExercisesModel(Parcel in) {
        name = in.readString();
        description = in.readString();
        muscleGroup = in.readString();
        muscleWorked = in.readString();
        videoPath = in.readString();
        imagePath = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(muscleGroup);
        dest.writeString(muscleWorked);
        dest.writeString(videoPath);
        dest.writeString(imagePath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ExercisesModel> CREATOR = new Creator<ExercisesModel>() {
        @Override
        public ExercisesModel createFromParcel(Parcel in) {
            return new ExercisesModel(in);
        }

        @Override
        public ExercisesModel[] newArray(int size) {
            return new ExercisesModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public String getMuscleWorked() {
        return muscleWorked.replace(" \n", ", ");
    }

    public void setMuscleWorked(String muscleWorked) {
        this.muscleWorked = muscleWorked;
    }

    public List<EquipmentModel> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<EquipmentModel> equipment) {
        this.equipment = equipment;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<WorkoutModel> getWorkout() {
        return workout;
    }

    public void setWorkout(List<WorkoutModel> workout) {
        this.workout = workout;
    }
}
