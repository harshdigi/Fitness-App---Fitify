/*
 * Creator: Harsh Ahuja on 18/06/21, 9:43 AM Last modified: 08/06/21, 9:45 AM Copyright: All rights reserved Ⓒ 2021 http://digitaldealsolution.in
 *
 */

package in.digitaldealsolution.fitify.utility;

import java.util.ArrayList;

import in.digitaldealsolution.fitify.model.ExercisesModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ExerciseApi {
    @GET
    Call<ArrayList<ExercisesModel>> getAllExercises(@Url String uid);
}

