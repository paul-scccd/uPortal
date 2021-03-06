/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.portal.groups;

import java.util.Iterator;
import java.util.Set;

import javax.naming.Name;

/**
 * An <code>IEntityGroup</code> is a composite, or non-leaf <code>IGroupMember</code>.
 * It contains <code>IEntities</code> and other <code>IEntityGroups</code>.
 * <p>
 * The api defines methods for adding a member to, and removing it from, a group, 
 * though not vice versa.  (Although there is nothing to prevent a given <code>IGroupMember</code>
 * implementation from storing references to its containing groups.)  These methods only
 * change the group structure in memory.
 * <p>
 *   <code>addChild(IGroupMember gm)</code><br>
 *   <code>removeChild(IGroupMember gm)</code><br>
 * <p>
 * The following methods commit changes in the group structure to the
 * persistent store:
 * <p>
 *   <code>delete()</code> - delete the group and its memberships<br>
 *   <code>update()</code>  - insert or update the group, as appropriate<br>
 *   <code>updateMembers()</code> - insert/update/delete group memberships as appropriate<br>
 * <p>
 * The following methods were added to permit an <code>IEntityGroup</code> to function
 * within a composite group service:
 * <p>
 *   <code>getLocalKey()</code> - returns the key within the service of origin.<br>
 *   <code>getServiceName()</code> - returns the Name of the group service of origin.<br>
 *   <code>setLocalGroupService()</code> - sets the group service of origin.<br>
 * <p>
 *
 * @author Dan Ellentuck
 */
public interface IEntityGroup extends IGroupMember {

    /**
     * Answers if this <code>IGroupMember</code> has any members.
     * @return boolean
     */
    boolean hasMembers() throws GroupsException;

    /**
     * Answers if <code>IGroupMember</code> gm is a member of <code>this</code>.
     */
    boolean contains(IGroupMember gm) throws GroupsException;

    /**
     * Answers if <code>IGroupMember</code> gm is a recursive member of <code>this</code>.
     * @return boolean
     * @param gm org.jasig.portal.groups.IGroupMember
     */
    boolean deepContains(IGroupMember gm) throws GroupsException;

    /**
     * Returns a collection of this <code>IGroupMember's</code> children.
     */
    Set<IGroupMember> getChildren() throws GroupsException;

    /**
     * Returns a collection of this <code>IGroupMember's</code> recursively-retrieved
     * <code>IGroupMembers</code>.
     */
    Set<IGroupMember> getDescendants() throws GroupsException;

/**
 * Adds <code>IGroupMember</code> gm to this group, but does not commit it to the
 * data store.  Use <code>updateMembers()</code> to commit memberships to the data store.
 * @param gm org.jasig.portal.groups.IGroupMember
 * @exception GroupsException is thrown if the member is a group and
 * this group already has a group with the same name or if the addition
 * of the group creates a circular reference.
 */
  void addChild(IGroupMember gm) throws GroupsException;

/**
 * Deletes the <code>IEntityGroup</code> from the data store.
 * @exception GroupsException if the delete cannot be performed. 
 */
  void delete() throws GroupsException;
/**
 * Returns the name of the group creator.  May be null.
 * @return String
 */
  String getCreatorID();
/**
 * Returns the group description, which may be null.
 * @return String
 */
  String getDescription();
/**
 * Returns the key from the group service of origin.
 * @return String
 */
  String getLocalKey();
/**
 * Returns the group name.
 * @return String
 */
  String getName();
/**
 * Returns the Name of the group service of origin.
 * @return String
 */
  Name getServiceName();
/**
 * Answers if this <code>IEntityGroup</code> can be changed or deleted.
 * @return boolean
 * @exception GroupsException
 */
  boolean isEditable() throws GroupsException;
/**
 * Removes the <code>IGroupMember</code> from this group, but does not remove the
 * membership from the data store.
 * @param gm org.jasig.portal.groups.IGroupMember
 */
  void removeChild(IGroupMember gm) throws GroupsException;
/**
 * @param userID String (required)
 */
  void setCreatorID(String userID);
/**
 * @param name String (may be null)
 */
  void setDescription(String name);
/**
 * Sets the group name which must be unique within any of its containing 
 * groups.  
 * @param name String
 * @exception GroupsException
 */
  void setName(String name) throws GroupsException;
/**
 * Commit the <code>IEntityGroup</code> AND ITS MEMBERSHIPS to the data store.
 * @exception GroupsException if the update cannot be performed. 
 */
  void update() throws GroupsException;
/**
 * Commit this <code>IEntityGroup's</code> memberships to the data store.
 * @exception GroupsException if the update cannot be performed. 
 */
  void updateMembers() throws GroupsException;

/**
 * Sets the group service of origin.
 */
  void setLocalGroupService(IIndividualGroupService groupService) throws GroupsException;
}
