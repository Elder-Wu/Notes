/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wuzhanglao.niubi.utils.nine_old_android;

/**
 * Thrown when code requests a {@link Property} on a class that does
 * not expose the appropriate method or field.
 *
 * @see Property#of(Class, Class, String)
 */
public class NoSuchPropertyException extends RuntimeException {

    public NoSuchPropertyException(String s) {
        super(s);
    }

}
