GObjectNotifyContext *context = nqueue->context;
GParamSpec *pspecs_mem[16], **pspecs, **free_me = NULL;
GSList *slist;
guint n_pspecs = 0;
g_return_if_fail (nqueue->freeze_count > 0);
nqueue->freeze_count--;
if (nqueue->freeze_count) { 
	return;
	
}
g_return_if_fail (object->ref_count > 0);
//  pspecs = nqueue->n_pspecs > 16 ? free_me = g_new (GParamSpec*, nqueue->n_pspecs) : pspecs_mem;
/* set first entry to NULL since it's checked unconditionally */
pspecs[0] = NULL;
for (slist = nqueue->pspecs; slist; slist = slist->next) { 
	GParamSpec *pspec = slist->data;
	gint i = 0;
	/* dedup, make pspecs in the list unique */
	redo_dedup_check:
	if (pspecs[i] == pspec) { 
		continue;
		
	}
	if (++i < n_pspecs) { 
		goto redo_dedup_check;
		
	}
	pspecs[n_pspecs++] = pspec;
	
}
g_datalist_id_set_data (&object->qdata, context->quark_notify_queue, NULL);
if (n_pspecs) { 
	context->dispatcher (object, n_pspecs, pspecs);
	
}
g_free (free_me);
