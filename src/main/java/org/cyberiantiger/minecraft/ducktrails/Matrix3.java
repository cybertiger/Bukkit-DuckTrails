/*
 * Copyright 2015 Antony Riley
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cyberiantiger.minecraft.ducktrails;

import org.bukkit.util.Vector;

/**
 *
 * @author antony
 */
public final class Matrix3 {

    private final double[] vals;

    private Matrix3(double[] vals) {
        this.vals = vals;
    }

    public Matrix3(double aa, double ab, double ac, double ba, double bb, double bc, double ca, double cb, double cc) {
        this.vals = new double[] { aa, ab, ac, ba, bb, bc, ca, cb, cc };
    }

    public static Matrix3 rotateX(double ang) {
        double sin = Math.sin(ang);
        double cos = Math.cos(ang);
        return new Matrix3(1, 0, 0, 0, cos, sin, 0, -sin, cos);
    }

    public static Matrix3 rotateY(double ang) {
        double sin = Math.sin(ang);
        double cos = Math.cos(ang);
        return new Matrix3(cos, 0, sin, 0, 1, 0, -sin, 0, cos);
    }

    public static Matrix3 rotateZ(double ang) {
        double sin = Math.sin(ang);
        double cos = Math.cos(ang);
        return new Matrix3(cos, sin, 0, -sin, cos, 0, 0, 0, 1);
    }

    public Matrix3 multiply(double val) {
        double[] result = new double[9];
        for (int i = 0; i < 9; i++) {
            result[i] = vals[i] * val;
        }
        return new Matrix3(vals);
    }

    public Vector multiply(Vector val) {
        double x = val.getX();
        double y = val.getY();
        double z = val.getZ();
        return new Vector (x * vals[0] + y * vals[1] + z * vals[2], x * vals[3] + y * vals[4] + z * vals[5], x * vals[6] + y * vals[7] + z * vals[8]);
    }

    public Matrix3 multiply(Matrix3 val) {
        double[] result = new double[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[j*3 + i] = (vals[j*3] * val.vals[i]) + (vals[j*3+1] * val.vals[3+i]) + (vals[j*3+2] * val.vals[6+i]);
            }
        }
        return new Matrix3(result);
    }
}