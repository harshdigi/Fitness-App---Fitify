/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 13/06/21, 6:13 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.model;

import android.os.Parcelable;

public class BodyPartModels  {
    String name, image;
    public BodyPartModels(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
