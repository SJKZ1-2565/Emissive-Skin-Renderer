package com.sjkz1.minetils.utils.enums;

public class SkinPart {
	public enum Part
	{
		//TODO Add all skin part
		HEAD(16,32);

		private int maxUvX;
		public int getMaxUvX() {
			return maxUvX;
		}


		public int getMaxUvY() {
			return maxUvY;
		}


		private int maxUvY;


		Part(int maxUvY, int maxUvX)
		{
			this.maxUvX = maxUvX;
			this.maxUvY = maxUvY;
		}
	}
}
