package ethereum;

import java.math.BigInteger;

/**
 * une entrée de conduite
 */
public class Entry {

    /**
     * rotation du volant en degrés
     */
    private Integer rot;

    /**
     * RPM du moteur
     */
    private Integer rpm;

    /**
     * vitesse en KM/H
     */
    private Integer spd;

    // ================================================================================
    // Constructors
    // ================================================================================

    /**
     * créé une nouvelle entrée de donnée de conduite
     */
    public Entry() {
	super();
	this.rot = null;
	this.rpm = null;
	this.spd = null;
    }

    // ================================================================================
    // Getters
    // ================================================================================

    /**
     * @return rotation du volant en degrés
     */
    public BigInteger getRot() {
	return BigInteger.valueOf(this.rot);
    }

    /**
     * @return RPM du moteur
     */
    public BigInteger getRpm() {
	return BigInteger.valueOf(this.rpm);
    }

    /**
     * @return vitesse en KM/H
     */
    public BigInteger getSpd() {
	return BigInteger.valueOf(this.spd);
    }

    // ================================================================================
    // Setters
    // ================================================================================

    /**
     * @param rot
     *            the rot to set
     */
    public void setRot(Integer rot) {
	this.rot = rot;
    }

    /**
     * @param rpm
     *            the rpm to set
     */
    public void setRpm(Integer rpm) {
	this.rpm = rpm;
    }

    /**
     * @param spd
     *            the spd to set
     */
    public void setSpd(Integer spd) {
	this.spd = spd;
    }

    // ================================================================================
    // Class Methods
    // ================================================================================

    /**
     * @return true si tout les champs sont complets, false sinon
     */
    public boolean isValid() {
	return this.spd != null && this.rpm != null && this.rot != null;
    }

}
