/*
 * Copyright 2017 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.ehviewer.client;

/*
 * Created by Hippo on 1/18/2017.
 */

import retrofit2.adapter.rxjava.Result;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * A base {@link Subscriber} for {@link EhClient}.
 */
public abstract class EhSubscriber<T extends EhResult> extends Subscriber<Result<T>> {

  @Override
  public void onCompleted() {}

  @Override
  public void onError(Throwable e) {
    EhReactiveX.handleError(e, new Action1<Throwable>() {
      @Override
      public void call(Throwable throwable) {
        onFailure(throwable);
      }
    });
  }

  @Override
  public void onNext(Result<T> result) {
    EhReactiveX.handleResult(result, new Func1<T, Void>() {
      @Override
      public Void call(T t) {
        onSuccess(t);
        return null;
      }
    });
  }

  /**
   * Called if we get the result.
   */
  public abstract void onSuccess(T t);

  /**
   * Called if an error occurred.
   */
  public abstract void onFailure(Throwable e);
}