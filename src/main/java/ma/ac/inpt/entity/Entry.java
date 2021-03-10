package ma.ac.inpt.entity;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class Entry {
    private SimpleStringProperty zone;
    private SimpleObjectProperty<Date> date_entry;
    private SimpleFloatProperty p;
    private SimpleFloatProperty mg;
    private SimpleFloatProperty n;
    private SimpleFloatProperty k;
    private SimpleFloatProperty cu;

    public Entry(String zone, Date date_entry, float p, float mg, float n, float k, float cu) {
        this.zone = new SimpleStringProperty(zone);
        this.date_entry = new SimpleObjectProperty<>(date_entry);
        this.p = new SimpleFloatProperty(p);
        this.mg = new SimpleFloatProperty(mg);
        this.n = new SimpleFloatProperty(n);
        this.k = new SimpleFloatProperty(k);
        this.cu = new SimpleFloatProperty(cu);
    }

    public String getZone() {
        return zone.get();
    }

    public SimpleStringProperty zoneProperty() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone.set(zone);
    }

    public Date getDate_entry() {
        return date_entry.get();
    }

    public SimpleObjectProperty<Date> date_entryProperty() {
        return date_entry;
    }

    public void setDate_entry(Date date_entry) {
        this.date_entry.set(date_entry);
    }

    public float getP() {
        return p.get();
    }

    public SimpleFloatProperty pProperty() {
        return p;
    }

    public void setP(float p) {
        this.p.set(p);
    }

    public float getMg() {
        return mg.get();
    }

    public SimpleFloatProperty mgProperty() {
        return mg;
    }

    public void setMg(float mg) {
        this.mg.set(mg);
    }

    public float getN() {
        return n.get();
    }

    public SimpleFloatProperty nProperty() {
        return n;
    }

    public void setN(float n) {
        this.n.set(n);
    }

    public float getK() {
        return k.get();
    }

    public SimpleFloatProperty kProperty() {
        return k;
    }

    public void setK(float k) {
        this.k.set(k);
    }

    public float getCu() {
        return cu.get();
    }

    public SimpleFloatProperty cuProperty() {
        return cu;
    }

    public void setCu(float cu) {
        this.cu.set(cu);
    }
}
