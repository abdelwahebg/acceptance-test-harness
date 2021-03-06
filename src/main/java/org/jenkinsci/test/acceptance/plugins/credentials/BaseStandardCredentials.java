/*
 * The MIT License
 *
 * Copyright 2016 CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkinsci.test.acceptance.plugins.credentials;

import java.util.concurrent.Callable;

import org.jenkinsci.test.acceptance.po.Control;
import org.jenkinsci.test.acceptance.po.PageArea;
import org.jenkinsci.test.acceptance.po.PageObject;

public abstract class BaseStandardCredentials extends Credential {

    public final Control description = control("description");
    public final Control scope = control("scope");
    private final Control delete = control("repeatable-delete");

    protected BaseStandardCredentials(PageObject context, String path) {
        super(context, path);
    }

    protected BaseStandardCredentials(PageArea area, String relativePath) {
        super(area, relativePath);
    }

    public void setId(String id) {
        control("advanced-button").click();
        control("id").set(id);
    }
    
    /**
     * Clicks the delete credential button and wait until it is not present anymore
     */
    public void delete() {
        delete.click();
        // Wait for element not present to avoid errors
        waitFor().until(new Callable<Boolean>() {
            
            @Override
            public Boolean call() throws Exception {
                return !isPresent();
            }
        });
    }
    
    /**
     * If the delete button is present, the credential still exists in the page.
     * 
     * @return true if the credential is present in the page. False otherwise.
     */
    private boolean isPresent() {
        return this.delete.exists();
    }

}
