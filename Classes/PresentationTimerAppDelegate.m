//
//  PresentationTimerAppDelegate.m
//  PresentationTimer
//
//  Created by 村上 卓弥 on 08/09/13.
//  Copyright __MyCompanyName__ 2008. All rights reserved.
//

#import "PresentationTimerAppDelegate.h"
#import "PresentationTimerViewController.h"

@implementation PresentationTimerAppDelegate

@synthesize window;
@synthesize viewController;


- (void)applicationDidFinishLaunching:(UIApplication *)application {    
    
    // Override point for customization after app launch    
    [window addSubview:viewController.view];
    [window makeKeyAndVisible];
}


- (void)dealloc {
    [viewController release];
    [window release];
    [super dealloc];
}


@end
