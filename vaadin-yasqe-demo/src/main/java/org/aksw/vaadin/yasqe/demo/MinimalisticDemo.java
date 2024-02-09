package org.aksw.vaadin.yasqe.demo;

import org.aksw.vaadin.yasqe.Yasqe;
import org.aksw.vaadin.yasqe.YasqeConfig;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route(MinimalisticDemo.NAV)
public class MinimalisticDemo extends VerticalLayout
{
    private static final long serialVersionUID = 1L;
    public static final String NAV = "/minimalistic";

    public MinimalisticDemo() {
        setSizeFull();

        YasqeConfig config = new YasqeConfig();

        // The persistence id is used to store the content in the browser's local store
        config.setPersistenceId("yasqe-persistenceid-1");
        config.setPrefixCcApi("http://prefix.cc/");
        // config.setResizeable(false);

        // Create and add the MapContainer (which contains the map) to the UI
        final Yasqe yasqe = new Yasqe(config);
        yasqe.setSizeFull();
        this.add(yasqe);

        // Setting the value will override local store content!
        // yasqe.addPrefixes(Map.of("foo", "urn:bar"));
        // yasqe.setValue("SELECT * { ?s ?p ?o } LIMIT 10");

        yasqe.addQueryButtonListener(ev -> {
            new Notification("Is abort? " + ev.isAbort() + " - " + ev.getValue(), 5000).open();
            ev.getSource().updateQueryButton(!ev.isAbort(), null);
        });

        // System.out.println(yasqe.getValue());
    }
}
