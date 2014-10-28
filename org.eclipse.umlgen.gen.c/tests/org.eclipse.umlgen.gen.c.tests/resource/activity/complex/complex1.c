/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Obeo - initial API and implementation
 *******************************************************************************/
printf("first line");
for(int i=0;i<10;i++) { 
	printf("in for");
	while(true) { 
		if (i < 4) { 
			return;
			
		}
		printf("after return");
		if (i < 8) { 
			printf("then clause 1");
			
		} else { 
			printf("else clause 1");
			
		}
		if (i < 10) { 
			printf("then clause 3");
			
		} else { 
			if (i > 15) { 
				printf("else clause 3");
				
			}
			while(false) { 
				printf("in while");
				switch (i) {
				case 0 :
					printf("0");
					break;
				case 1 :
					printf("1");
					continue;
				default :
					printf("default");
				
				}
				printf("after switch");
				
			}
			
		}
		while(false) { 
			printf("in while");
			switch (i) {
			case 0 :
				printf("0");
				break;
			case 1 :
				printf("1");
				continue;
			default :
				printf("default");
			
			}
			printf("after switch");
			
		}
		
	}
	
}
if (i < 10) { 
	do { 
		printf("in do loop");
		if (i<5) { 
			printf("A");
			if (i<4) { 
				printf("AA");
				
			} else { 
				printf("AB");
				
			}
			
		} else { 
			printf("B");
			if (i<4) { 
				printf("BA");
				
			} else { 
				printf("BB");
				
			}
			
		}
		
	}while(true);
	
} else { 
	printf("C");
	
}
printf("last line");
