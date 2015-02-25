package com.telerik.examples.common.contracts;

/**
 * Handler for interacting with transitions.
 */
public interface TransitionHandler {

    /**
     * Updates the current transition by a certain step.
     *
     * @param step the step used to update the transition.
     */
    void updateTransition(float step);
}
