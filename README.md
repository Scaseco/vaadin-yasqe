# vaadin-yasqe
Vaadin bindings for Yasqe

<img src="docs/images/2024-02-09-vaadin-yasqe.png" width="550px" />

## Usage
See the demo for details.

```java
public class MyComponent extends VerticalLayout
{
    public MyComponent() {
        setSizeFull();
        YasqeConfig config = new YasqeConfig();
        config.setResizeable(false);
        Yasqe yasqe = new Yasqe(config);
        yasqe.setSizeFull();
        this.add(yasqe);

        yasqe.addQueryButtonListener(ev -> {
            new Notification("Is abort? " + ev.isAbort() + " - " + ev.getValue(), 5000)
                .open();
            ev.getSource().updateQueryButton(!ev.isAbort(), null);
        });
    }
}
```

## Run the demo

* Clone this repository
* Run `mvn install && mvn -f vaadin-yasqe-demo spring-boot:run`
* Open <a href="http://localhost:8080" target="_blank">http://localhost:8080</a>

