package com.telerik.examples.examples.listview;

public interface PropertyChangeNotifier {
    void setPropertyChangeListener(PropertyChangeListener listener);
    PropertyChangeListener getPropertyChangeListener();
    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
}
