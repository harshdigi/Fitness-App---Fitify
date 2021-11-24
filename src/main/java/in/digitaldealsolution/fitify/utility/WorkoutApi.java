/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 12/06/21, 9:43 PM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.utility;

import java.util.ArrayList;

import in.digitaldealsolution.fitify.model.ExercisesModel;
import in.digitaldealsolution.fitify.model.WorkoutModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WorkoutApi {
    @GET
    Call<ArrayList<WorkoutModel>> getAllWorkout(@Url String uid);
}
