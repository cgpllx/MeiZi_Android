package com.meizitu.core;

import android.os.Bundle;
import android.support.annotation.NonNull;

public interface EasyPresenterState {

    void saveInstanceState(@NonNull Bundle out);


    EasyPresenterState restoreInstanceState(Bundle in);

}
