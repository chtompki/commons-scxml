/*
 *
 *   Copyright 2005 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.apache.commons.scxml;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.scxml.model.Transition;
import org.apache.commons.scxml.model.TransitionTarget;

/**
 * The registry where SCXML listeners are recorded for Observable
 * objects. The registry performs book keeping functions and notifies
 * all listeners of the events of interest.
 *
 */
public class NotificationRegistry {

    /**
     * The Map of all listeners keyed by Observable.
     */
    private Map regs = new HashMap();

    /**
     * Constructor.
     */
    public NotificationRegistry() {
        super();
    }

    /**
     * Register this SCXMLListener for this Observable.
     *
     * @param source The observable this listener wants to listen to
     * @param lst The listener
     */
    public final void addListener(final Observable source,
            final SCXMLListener lst) {
        Set entries = (Set) regs.get(source);
        if (entries == null) {
            entries = new HashSet();
            regs.put(source, entries);
        }
        entries.add(lst);
    }

    /**
     * Deregister this SCXMLListener for this Observable.
     *
     * @param source The observable this listener wants to stop listening to
     * @param lst The listener
     */
    public final void removeListener(final Observable source,
            final SCXMLListener lst) {
        Set entries = (Set) regs.get(source);
        if (entries != null) {
            entries.remove(lst);
            if (entries.size() == 0) {
                regs.remove(source);
            }
        }
    }

    /**
     * Inform all relevant listeners that a TransitionTarget has been
     * entered.
     *
     * @param source The Observable
     * @param state The TransitionTarget that was entered
     */
    public final void fireOnEntry(final Observable source,
            final TransitionTarget state) {
        Set entries = (Set) regs.get(source);
        if (entries != null) {
            for (Iterator iter = entries.iterator(); iter.hasNext();) {
                SCXMLListener lst = (SCXMLListener) iter.next();
                lst.onEntry(state);
            }
        }
    }

    /**
     * Inform all relevant listeners that a TransitionTarget has been
     * exited.
     *
     * @param source The Observable
     * @param state The TransitionTarget that was exited
     */
    public final void fireOnExit(final Observable source,
            final TransitionTarget state) {
        Set entries = (Set) regs.get(source);
        if (entries != null) {
            for (Iterator iter = entries.iterator(); iter.hasNext();) {
                SCXMLListener lst = (SCXMLListener) iter.next();
                lst.onExit(state);
            }
        }
    }

    /**
     * Inform all relevant listeners of a transition that has occured.
     *
     * @param source The Observable
     * @param from The source TransitionTarget
     * @param to The destination TransitionTarget
     * @param transition The Transition that was taken
     */
    public final void fireOnTransition(final Observable source,
            final TransitionTarget from, final TransitionTarget to,
            final Transition transition) {
        Set entries = (Set) regs.get(source);
        if (entries != null) {
            for (Iterator iter = entries.iterator(); iter.hasNext();) {
                SCXMLListener lst = (SCXMLListener) iter.next();
                lst.onTransition(from, to, transition);
            }
        }
    }
}
