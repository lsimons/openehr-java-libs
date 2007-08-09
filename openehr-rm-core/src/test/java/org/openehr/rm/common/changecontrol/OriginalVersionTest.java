/*
 * component:   "openEHR Reference Implementation"
 * description: "Class OriginalVersionTest"
 * keywords:    "unit test"
 *
 * author:      "Yin Su Lim <y.lim@chime.ucl.ac.uk>"
 * support:     "CHIME, UCL"
 * copyright:   "Copyright (c) 2006 UCL, UK"
 * license:     "See notice at bottom of class"
 *
 * file:        "$URL: http://svn.openehr.org/ref_impl_java/BRANCHES/RM-1.0-update/libraries/src/test/org/openehr/rm/common/changecontrol/OriginalVersionTest.java $"
 * revision:    "$LastChangedRevision: 50 $"
 * last_change: "$LastChangedDate: 2006-08-10 12:21:46 +0100 (Thu, 10 Aug 2006) $"
 */

/**
 * OriginalVersionTest
 *
 * @author Yin Su Lim
 * @version 1.0 
 */
package org.openehr.rm.common.changecontrol;

import java.util.HashSet;
import junit.framework.*;
import java.util.List;
import java.util.Set;
import org.openehr.rm.common.generic.Attestation;
import org.openehr.rm.common.generic.AuditDetails;
import org.openehr.rm.common.generic.PartyIdentified;
import org.openehr.rm.datatypes.quantity.datetime.DvDateTime;
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.datatypes.text.DvCodedText;
import org.openehr.rm.datatypes.text.TestCodePhrase;
import org.openehr.rm.support.identification.HierarchicalObjectID;
import org.openehr.rm.support.identification.LocatableReference;
import org.openehr.rm.support.identification.ObjectRef;
import org.openehr.rm.support.identification.ObjectVersionID;
import org.openehr.rm.support.identification.PartyRef;
import org.openehr.rm.support.identification.TestTerminologyID;
import org.openehr.rm.support.terminology.TerminologyService;
import org.openehr.rm.support.terminology.TestTerminologyService;

public class OriginalVersionTest extends TestCase {
    
    public OriginalVersionTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(OriginalVersionTest.class);
        
        return suite;
    }

    public void testConstructors() {
 
        ObjectVersionID uid = new ObjectVersionID("1.4.4.5::1.2.840.114.1.2.2::1");
        CodePhrase codePhrase =
                new CodePhrase(TestTerminologyID.SNOMEDCT, "revisionCode");
        DvCodedText codedText = new DvCodedText("complete", TestCodePhrase.ENGLISH,
                TestCodePhrase.LATIN_1, codePhrase,
                TestTerminologyService.getInstance());
        PartyIdentified pi = new PartyIdentified(new PartyRef(
                new HierarchicalObjectID("1-2-3-4-5"), 
                ObjectRef.Type.PARTY), "committer name", null);
        AuditDetails audit1 = new AuditDetails("12.3.4.5", pi, 
                new DvDateTime("2006-05-01T10:10:00"), codedText, null,
                TestTerminologyService.getInstance());
        ObjectRef lr = new LocatableReference(new ObjectVersionID(
                "1.23.51.66::1.2.840.114.1.2.2::2"), ObjectRef.Namespace.LOCAL,
                ObjectRef.Type.CONTRIBUTION, "");
        OriginalVersion<String> ov = new OriginalVersion<String>(uid, null, "A Party Info", 
                codedText, audit1, lr, null, null, null, false, 
                TestTerminologyService.getInstance());
        Set<ObjectVersionID> otherUids = new HashSet<ObjectVersionID>();
        otherUids.add(new ObjectVersionID("1.4.14.5::1.2.840.114.1.2.2::4.2.2"));
        OriginalVersion<String> ov2 = new OriginalVersion<String>(uid, null, "A Party Info", 
                codedText, audit1, lr, null, otherUids, null, true, 
                TestTerminologyService.getInstance());
        assertContructorError(uid, null, "A Party Info", 
                codedText, audit1, lr, null, otherUids, null, false, 
                TestTerminologyService.getInstance());
        assertContructorError(uid, null, "A Party Info", 
                codedText, audit1, lr, null, null, null, true, 
                TestTerminologyService.getInstance());
        
    }
    
    private void assertContructorError(ObjectVersionID uid, ObjectVersionID pVersionID,
            String data, DvCodedText codedText, AuditDetails audit, 
            ObjectRef contribution, String signature, 
            Set<ObjectVersionID> otherUids, List<Attestation> attestations,
            boolean isMerged, TerminologyService terminologyService) {

        try {
            OriginalVersion<String> ov = new OriginalVersion<String>(uid, pVersionID, data, 
                codedText, audit, contribution, signature, otherUids, attestations, 
                isMerged, TestTerminologyService.getInstance());
            fail("should throw exception, breach of isMerged validity");
        } catch (IllegalArgumentException iae) {
            
        }
    }    
}

/*
 *  ***** BEGIN LICENSE BLOCK *****
 *  Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the 'License'); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an 'AS IS' basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is OriginalVersionTest.java
 *
 *  The Initial Developer of the Original Code is Rong Chen.
 *  Portions created by the Initial Developer are Copyright (C) 2003-2004
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 * Software distributed under the License is distributed on an 'AS IS' basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 *  ***** END LICENSE BLOCK *****
 */
