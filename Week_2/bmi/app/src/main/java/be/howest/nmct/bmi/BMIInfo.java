package be.howest.nmct.bmi;

/**
 * Created by Jonathan on 12/02/2015.
 */
public class BMIInfo {
    float height = 1.7f;
    int mass = 70;
    float bmiIndex;

    public BMIInfo(float height, int mass) {
        this.height = height;
        this.mass = mass;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void recalculate() {
        //bmiindex berkenen
        bmiIndex = mass / (height * height);
    }

    @Override
    public String toString() {
        recalculate();
        return "Uw BMI bedraagt " + bmiIndex + ", u zit in de categorie " + Category.getCategory(bmiIndex);
    }

    public enum Category {

        GROOTONDERGEWICHT(0, 15),
        ERNSTIGONDERGEWICHT(15, 16),
        ONDERGEWICHT(16, 18.5f),
        NORMAAL(18.5f, 25),
        OVERGEWICHT(25, 30),
        MATIGOVERGEWICHT(30, 35),
        ERNSTIGOVERGEWICHT(35, 40),
        ZEERGROOTOVERGEWICHT(40, 60);

        public float lowerBoundary, upperBoundary;

        Category(float lowerBoundary, float upperBoundary) {
            this.lowerBoundary = lowerBoundary;
            this.upperBoundary = upperBoundary;
        }

        public float getLowerBoundary() {
            return lowerBoundary;
        }

        public float getUpperBoundary() {
            return upperBoundary;
        }

        public Boolean isInBoundary(float index) {
            if (index > getLowerBoundary() && index <= getUpperBoundary()) {
                return true;
            } else {
                return false;
            }
        }

        public static Category getCategory(float index)
        {
            //keySet itereert(herhaalt) over de waardes van elke foreach lus
            for(Category category : Category.values())
            {
                if(category.isInBoundary(index))
                    return category;
            }
            return null;
        }
    }
}