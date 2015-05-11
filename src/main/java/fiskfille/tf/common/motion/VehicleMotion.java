package fiskfille.tf.common.motion;

/**
 * @author gegy1000, FiskFille
 */
public class VehicleMotion
{
    private double forwardVelocity;
    private double horizontalVelocity;
    private int nitro;
    private boolean boosting;

    private int jetRoll;

    public boolean isBoosting()
    {
        return boosting;
    }

    public VehicleMotion setBoosting(boolean boosting)
    {
        this.boosting = boosting;
        return this;
    }

    public double getForwardVelocity()
    {
        return forwardVelocity;
    }

    public VehicleMotion setForwardVelocity(double vel)
    {
        this.forwardVelocity = vel;
        return this;
    }

    public double getHorizontalVelocity()
    {
        return horizontalVelocity;
    }

    public VehicleMotion setHorizontalVelocity(double vel)
    {
        this.horizontalVelocity = vel;
        return this;
    }

    public int getNitro()
    {
        return nitro;
    }

    public VehicleMotion setNitro(int nitro)
    {
        this.nitro = nitro;
        return this;
    }

    public int getJetRoll()
    {
        return jetRoll;
    }

    public VehicleMotion setJetRoll(int roll)
    {
        this.jetRoll = roll;
        return this;
    }
}
