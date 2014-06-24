Boolean redisplay;
Mask    window_mask;
XSetWindowAttributes attributes;
XtTranslations save;
redisplay = FALSE;
if (old->core.tm.translations != new->core.tm.translations) {
	save = new->core.tm.translations;
	new->core.tm.translations = old->core.tm.translations;
	_XtMergeTranslations(new, save, XtTableReplace);
}
if (XtIsRealized(old)) {
	window_mask = 0;
	if (old->core.background_pixel != new->core.background_pixel
	            && new->core.background_pixmap == XtUnspecifiedPixmap) {
		attributes.background_pixel  = new->core.background_pixel;
		window_mask |= CWBackPixel;
		redisplay = TRUE;
	}
	if (old->core.background_pixmap != new->core.background_pixmap) {
		if (new->core.background_pixmap == XtUnspecifiedPixmap) {
			window_mask |= CWBackPixel;
			attributes.background_pixel  = new->core.background_pixel;
		} else { 
			attributes.background_pixmap = new->core.background_pixmap;
			window_mask &= ~CWBackPixel;
			window_mask |= CWBackPixmap;
		}
		redisplay = TRUE;
	}
	if (old->core.border_pixel != new->core.border_pixel
	            && new->core.border_pixmap == XtUnspecifiedPixmap) {
		attributes.border_pixel  = new->core.border_pixel;
		window_mask |= CWBorderPixel;
	}
	if (old->core.border_pixmap != new->core.border_pixmap) {
		if (new->core.border_pixmap == XtUnspecifiedPixmap) {
			window_mask |= CWBorderPixel;
			attributes.border_pixel  = new->core.border_pixel;
		} else { 
			attributes.border_pixmap = new->core.border_pixmap;
			window_mask &= ~CWBorderPixel;
			window_mask |= CWBorderPixmap;
		}
	}
	if (old->core.depth != new->core.depth) {
		XtAppWarningMsg(XtWidgetToApplicationContext(old),
		                    "invalidDepth","setValues",XtCXtToolkitError,
		               "Can't change widget depth", (String *)NULL, (Cardinal *)NULL);
		new->core.depth = old->core.depth;
	}
	if (old->core.colormap != new->core.colormap) {
		window_mask |= CWColormap;
		attributes.colormap = new->core.colormap;
	}
	if (window_mask != 0) {
		XChangeWindowAttributes(
		                XtDisplay(new), XtWindow(new), window_mask, &attributes);
	}
	if (old->core.mapped_when_managed != new->core.mapped_when_managed) {
		Boolean mapped_when_managed = new->core.mapped_when_managed;
		new->core.mapped_when_managed = !mapped_when_managed;
		XtSetMappedWhenManaged(new, mapped_when_managed);
	}
}
return redisplay;